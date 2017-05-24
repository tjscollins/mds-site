(ns mds.routes.admin
  (:require [clojure.data.json :as json]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.auth.backends :as backends]
            [buddy.sign.jwt :as jwt]
            [compojure.core :refer [defroutes GET POST]]
            [mds.layout :as layout]
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
        user "admin"
        token (jwt/sign {:user "admin"} auth-secret)]
    (if (= user (get data "password"))
      (response/ok {:token token})
      (response/found "/admin"))))

(defroutes admin-routes
  (GET "/login" []
       (login-page))
  (POST "/login" request
        (println "Login request: " (:form-params request))
        (attempt-login request))
  (GET "/admin" request
       (if (not (authenticated? request))
         (response/found "/login")
         (admin-page))))
