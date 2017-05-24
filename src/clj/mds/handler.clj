(ns mds.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [compojure.route :as route]
            [mount.core :as mount]
            [mds.env :refer [defaults]]
            [mds.layout :refer [error-page]]
            [mds.middleware :as middleware]
            [mds.routes.admin :refer [admin-routes auth-backend]]
            [mds.routes.api :refer [api-routes]]
            [mds.routes.web :refer [web-routes]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [buddy.auth.middleware :refer [wrap-authentication]]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))


(def app-routes
  (routes
   (-> admin-routes
       (wrap-routes middleware/wrap-csrf)
       (wrap-routes middleware/wrap-csrf)
       (wrap-json-body)
       (wrap-json-response)
       (wrap-authentication auth-backend))
   (-> api-routes
       (wrap-json-body)
       (wrap-json-response))
   (-> #'web-routes
       (wrap-routes middleware/wrap-csrf)
       (wrap-routes middleware/wrap-formats))
   (route/not-found
    (:body
     (error-page {:status 404
                  :title "page not found"})))))


(defn app [] (middleware/wrap-base #'app-routes))
