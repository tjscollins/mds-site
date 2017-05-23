(ns mds.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [compojure.route :as route]
            [mount.core :as mount]
            [mds.env :refer [defaults]]
            [mds.layout :refer [error-page]]
            [mds.middleware :as middleware]
            [mds.routes.home :refer [home-routes]]
            [mds.routes.api :refer [api-routes]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
   (-> api-routes
       (wrap-json-body)
       (wrap-json-response))
    (-> #'home-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))


(defn app [] (middleware/wrap-base #'app-routes))
