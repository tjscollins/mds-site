(ns mds.routes.home
  (:require [mds.layout :as layout]
            [mds.db.core :refer [*db*] :as db]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [markdown.core :refer [md-to-html-string]]
            [selmer.parser :refer [render-file]]
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
    (layout/render
     "home.html" {:page-type 1
                  :title "CNMI Scholars"
                  :brand "CNMI Scholars"
                  :backdrop-txt "The Million Dollar Scholars"
                  :mds-grp-photo (str aws-url
                                      (get bg-dirs res)
                                      "DSC_0150.jpg")
                  :mds-grp-photo-2 (str aws-url
                                        (get bg-dirs res)
                                        "DSC_0248.jpg")
                  :mds-info (slurp "resources/docs/mds-info-blurb.md")
                  :student-info (student-markdown selected-student)
                  :student-photo (str aws-url
                                      (get bg-dirs res)
                                      (:bio_photo selected-student))})))
(defn photo-path
  [photo]
  (str aws-url
       (get bg-dirs 0)
       photo))

(defn student-photo-markup
  [student]
  (render-file "student_photo.html"
               {:student-id (:id student)
                :src (photo-path (:bio_photo student))
                :name (clojure.string/join " " (map student [:first_name :last_name]))}))

(defn student-story-markup
  [student]
  (let [photo-1 (:stry_photo_1 student)
        photo-2 (:stry_photo_2 student)
        photo-3 (:stry_photo_3 student)]
    (render-file "student_story.html"
                 {:id (str "student-story-" (:id student))
                  :name (clojure.string/join " " (map student [:first_name :last_name]))
                  :img-1 (photo-path photo-1)
                  :img-2 (photo-path photo-2)
                  :img-3 (photo-path photo-3)})))

(defn stories-page [image]
  (let [students (db/get-all-students)]
    (layout/render
     "stories.html" {:title "Our Stories"
                     :brand "CNMI Scholars"
                     :student-photo-1 (str
                                       aws-url
                                       (get bg-dirs res)
                                       image)
                     :student-imgs (apply str (map student-photo-markup students))
                     :student-stories (apply str (map student-story-markup students))})))

(defroutes home-routes
  (GET "/" request
       (home-page))
  (GET "/docs" []
       (-> (response/ok (-> "docs/docs.md" io/resource slurp))
           (response/header "Content-Type" "text/plain; charset=utf-8")))
  (GET "/stories" request
       (stories-page "DSC_0021.jpg"))
  (GET "/create/:student" []
       ()))
