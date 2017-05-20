(ns mds.routes.home
  (:require [mds.layout :as layout]
            [mds.db.core :refer [*db*] :as db]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [markdown.core :refer [md-to-html-string]]
            ))

(def aws-url "https://s3-us-west-1.amazonaws.com/mdscontentfiles/MDS_2017/")
(def bg-dirs ["736x460/" "1068x648/" "1440x900/"])
(def res 0)

(defn student-markdown
  "Generate markdown for a student selected from the DB"
  [student]
  (str "# Meet "
       (:first_name student)
       " "
       (:last_name student)
       "\n"
       (:bio_blurb student)
       "\n\n"
       "[Meet our other students](/stories)"))

(defn home-page []
  (let [students (db/get-all-students)
        selected-student (nth students (rand-int (count students)))]
    (layout/render "home.html" {:title "CNMI Scholars"
                                :brand "CNMI Scholars"
                                :backdrop-txt "The Million Dollar Scholars"
                                :mds-grp-photo (str aws-url
                                                    (get bg-dirs res)
                                                    "DSC_0150.jpg")
                                :mds-grp-photo-2 (str aws-url
                                                      (get bg-dirs res)
                                                      "DSC_0248.jpg")
                                :mds-info (slurp "resources/docs/mds-info-blurb.md")
                                :student-info (student-markdown selected-student) ;(slurp "resources/docs/student-blurb.md")
                                :student-photo (str aws-url
                                                    (get bg-dirs res)
                                                    (:bio_photo selected-student))})))

(defn stories-page [image]
  (layout/render  "stories.html" {:title "Our Stories"
                                  :brand "CNMI Scholars"
                                  :student-photo-1 (str
                                                    aws-url
                                                    (get bg-dirs res)
                                                    image)}))

(defroutes home-routes
  (GET "/" []
       (home-page))
  (GET "/docs" []
       (-> (response/ok (-> "docs/docs.md" io/resource slurp))
           (response/header "Content-Type" "text/plain; charset=utf-8")))
  (GET "/stories" []
       (stories-page "DSC_0021.jpg"))
  (GET "/create/:student" []
       ()))
