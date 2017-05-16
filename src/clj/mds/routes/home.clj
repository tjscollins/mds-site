(ns mds.routes.home
  (:require [mds.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render "home.html" {:title "CNMI Scholars"
                              :brand "CNMI Scholars"
                              :backdrop-1-txt "We are the Million Dollar Scholars"
                              :blurb-header "<h1>We Make Scholars, Everyday</h1>"
                              :blurb-body  "<p>Here in the CNMI, we are making scholars.</p>"}))

(defroutes home-routes
  (GET "/" []
       (home-page))
  (GET "/docs" []
       (-> (response/ok (-> "docs/docs.md" io/resource slurp))
       (response/header "Content-Type" "text/plain; charset=utf-8"))))

