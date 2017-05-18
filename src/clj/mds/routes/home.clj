(ns mds.routes.home
  (:require [mds.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [markdown.core :refer [md-to-html-string]]))

(defn home-page []
  (layout/render "home.html" {:title "CNMI Scholars"
                              :brand "CNMI Scholars"
                              :backdrop-1-txt "The Million Dollar Scholars"
                              :mds-info (slurp "resources/docs/mds-info-blurb.md")
                              :student-info (slurp "resources/docs/student-blurb.md")
                              :student-photo "https://placehold.it/1980x1400/330088?text=Student%20Photo"}))

(defn stories-page []
  (layout/render  "stories.html" {}))

(defroutes home-routes
  (GET "/" []
       (home-page))
  (GET "/docs" []
       (-> (response/ok (-> "docs/docs.md" io/resource slurp))
           (response/header "Content-Type" "text/plain; charset=utf-8")))
  (GET "/stories" []
       (stories-page)))
