(ns mds.test.db.core
  (:require [mds.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [mds.config :refer [env]]
            [mount.core :as mount]))

(def test-blurb "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tincidunt mattis quam, sit amet eleifend mi mattis sit amet. Donec molestie orci quis dolor porttitor semper. Nullam volutpat elit quis accumsan feugiat. Suspendisse sed justo vitae mauris rutrum tincidunt id a dui. Pellentesque a metus aliquet, hendrerit nunc non, sagittis sapien. Nulla sed magna nulla. Aliquam sed condimentum erat. Maecenas pellentesque orci a quam commodo aliquam.")

(use-fixtures
  :once
  (fn [f]
    (mount/start
     #'mds.config/env
     #'mds.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(use-fixtures
  :each
  (fn [f]
    (db/clear-students! *db*)
    (db/create-student! *db* {:id 1
                              :first_name "John"
                              :last_name "Doe"
                              :bio_blurb test-blurb
                              :bio_photo "DSC_0364.jpg"
                              :stry_photo_1 "DSC_0364.jpg"
                              :stry_photo_2 "DSC_0364.jpg"
                              :stry_photo_3 "DSC_0364.jpg"})
    (db/create-student! *db* {:id 2
                              :first_name "Jane"
                              :last_name "Doe"
                              :bio_blurb test-blurb
                              :bio_photo "DSC_0227.jpg"
                              :stry_photo_1 "DSC_0364.jpg"
                              :stry_photo_2 "DSC_0364.jpg"
                              :stry_photo_3 "DSC_0364.jpg"})
    (db/create-student! *db* {:id 3
                              :first_name "Janet"
                              :last_name "Doelittle"
                              :bio_blurb test-blurb
                              :bio_photo "DSC_0252.jpg"
                              :stry_photo_1 "DSC_0364.jpg"
                              :stry_photo_2 "DSC_0364.jpg"
                              :stry_photo_3 "DSC_0364.jpg"})
    (db/create-student! *db* {:id 4
                              :first_name "Bob"
                              :last_name "Dole"
                              :bio_blurb test-blurb
                              :bio_photo "DSC_0306.jpg"
                              :stry_photo_1 "DSC_0364.jpg"
                              :stry_photo_2 "DSC_0364.jpg"
                              :stry_photo_3 "DSC_0364.jpg"})
    (db/create-student! *db* {:id 4
                              :first_name "Richard"
                              :last_name "Nixon"
                              :bio_blurb test-blurb
                              :bio_photo "DSC_0306.jpg"
                              :stry_photo_1 "DSC_0364.jpg"
                              :stry_photo_2 "DSC_0364.jpg"
                              :stry_photo_3 "DSC_0364.jpg"})
    (db/create-student! *db* {:id 4
                              :first_name "Alexander"
                              :last_name "the Great"
                              :bio_blurb test-blurb
                              :bio_photo "DSC_0306.jpg"
                              :stry_photo_1 "DSC_0364.jpg"
                              :stry_photo_2 "DSC_0364.jpg"
                              :stry_photo_3 "DSC_0364.jpg"})
    (f)))

(deftest test-students
  (is (= {:id 1
          :first_name "John"
          :last_name "Doe"
          :bio_blurb test-blurb
          :bio_photo "DSC_0364.jpg"
          :stry_photo_1 "DSC_0364.jpg"
          :stry_photo_2 "DSC_0364.jpg"
          :stry_photo_3 "DSC_0364.jpg"}
         (db/get-student *db* {:id 1}))))
