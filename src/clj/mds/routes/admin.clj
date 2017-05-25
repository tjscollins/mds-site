(ns mds.routes.admin
  (:require [clojure.data.json :as json]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.auth.backends :as backends]
            [buddy.sign.jwt :as jwt]
            [buddy.hashers :as hashers]
            [compojure.core :refer [defroutes GET POST]]
            [mds.layout :as layout]
            [mds.db.core :refer [*db*] :as db]
            [ring.util.http-response :as response]
            ))

(def auth-secret "mds-rocks")
(def auth-backend (backends/jws {:secret auth-secret}))


(defn login-page []
  (layout/render "login.html" {}))

(defn admin-page []
  (layout/render "admin.html" {}))

(defn attempt-login
  [request]
  (let [data (:form-params request)
        user (get-in data ["username"])
        pass (get-in data ["password"])
        hpwd (:pass (db/get-user *db* {:username user}))
        token (jwt/sign {:user user :pass hpwd} auth-secret)]
    (if (hashers/check pass hpwd)
      (response/ok {:token token})
      (response/found "/admin"))))

(defroutes admin-routes
  (GET "/login" []
       (login-page))
  (POST "/login" request
        (println "Login request: " (hashers/derive (get-in request [:form-params "password"])))
        (attempt-login request))
  (GET "/admin" request
       (if (not (authenticated? request))
         (response/found "/login")
         (admin-page))))
