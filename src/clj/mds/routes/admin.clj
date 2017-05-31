(ns mds.routes.admin
  (:require [clojure.data.json :as json]
            [buddy.auth :refer [throw-unauthorized]]
            [buddy.auth.backends :as backends]
            [buddy.sign.jwt :as jwt]
            [buddy.hashers :as hashers]
            [compojure.core :refer [defroutes GET POST]]
            [mds.layout :as layout]
            [mds.db.core :refer [*db*] :as db]
            [ring.util.http-response :as response])
  (:import [java.util.Date]))

(def auth-secret "mds-rocks")
(def auth-backend (backends/jws {:secret auth-secret}))

(defn authenticated?
  "Check for and validate JWT in cookie"
  [request]
  (let [auth (get-in request [:cookies "Authorization" :value])]
    (try
      (let [{user :user} (jwt/unsign auth auth-secret)]
        (= true (:admin (db/get-user *db* {:username user}))))
      (catch Exception e
        false))))

(defn now [] (java.util.Date.))
(def one-day-ms (* 24 60 60 1000))
(defn tomorrow [] (java.util.Date. (+ (.getTime (now)) one-day-ms)))

(defn login-page [request]
  (let [invalid? (get-in request [:query-params "invalid_login"])]
    (println (str "Invalid? " invalid?))
    (layout/render "login.html" {:invalid invalid?})))

(defn admin-page []
  ;; (let [admin-params (db/get-admin-params *db*)])
  (layout/render "admin.html" {}))

(defn attempt-login
  [request]
  (println (str "Time now: " (now) " Time Tomorrow: " (tomorrow)))
  (let [user (get-in request [:form-params "username"])
        pass (get-in request [:form-params "password"])
        hpwd (:pass (db/get-user *db* {:username user}))
        token (jwt/sign {:user user :exp (tomorrow) :iat (now)} auth-secret)
        cookies (:cookies request)]
    (if (hashers/check pass hpwd)
      (-> (response/found "admin")
          (response/set-cookie "Authorization" token {:http-only true
                                                      :path "/admin"
                                                      :max-age (/ one-day-ms 1000)}))
      (response/found "/login?invalid_login=true"))))

(defroutes admin-routes
  (GET "/login" request
       (login-page request))
  (POST "/login" request
        ;(println "Login request: " (hashers/derive (get-in request [:form-params "password"])))
        (attempt-login request))
  (GET "/admin" request
       (if (not (authenticated? request))
         (response/found "/login")
         (admin-page))))
