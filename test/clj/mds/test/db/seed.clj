(ns mds.test.db.seed
  (:require [mds.db.core :refer [*db*] :as db]))

(def test-blurb "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tincidunt mattis quam, sit amet eleifend mi mattis sit amet. Donec molestie orci quis dolor porttitor semper. Nullam volutpat elit quis accumsan feugiat. Suspendisse sed justo vitae mauris rutrum tincidunt id a dui. Pellentesque a metus aliquet, hendrerit nunc non, sagittis sapien. Nulla sed magna nulla. Aliquam sed condimentum erat. Maecenas pellentesque orci a quam commodo aliquam.")

(def seed
  {:students [{:id 1
               :first_name "John"
               :last_name "Doe"
               :bio_blurb test-blurb
               :bio_photo "DSC_0364.jpg"
               :stry_photo_1 "DSC_0364.jpg"
               :stry_photo_2 "DSC_0364.jpg"
               :stry_photo_3 "DSC_0364.jpg"}
              {:id 2
               :first_name "Jane"
               :last_name "Doe"
               :bio_blurb test-blurb
               :bio_photo "DSC_0227.jpg"
               :stry_photo_1 "DSC_0227.jpg"
               :stry_photo_2 "DSC_0227.jpg"
               :stry_photo_3 "DSC_0227.jpg"}
              {:id 3
               :first_name "Janet"
               :last_name "Doelittle"
               :bio_blurb test-blurb
               :bio_photo "DSC_0252.jpg"
               :stry_photo_1 "DSC_0252.jpg"
               :stry_photo_2 "DSC_0252.jpg"
               :stry_photo_3 "DSC_0252.jpg"}
              {:id 4
               :first_name "Bob"
               :last_name "Dole"
               :bio_blurb test-blurb
               :bio_photo "DSC_0306.jpg"
               :stry_photo_1 "DSC_0306.jpg"
               :stry_photo_2 "DSC_0306.jpg"
               :stry_photo_3 "DSC_0306.jpg"}
              {:id 5
               :first_name "Richard"
               :last_name "Nixon"
               :bio_blurb test-blurb
               :bio_photo "DSC_0306.jpg"
               :stry_photo_1 "DSC_0306.jpg"
               :stry_photo_2 "DSC_0306.jpg"
               :stry_photo_3 "DSC_0306.jpg"}
              {:id 6
               :first_name "Alexander"
               :last_name "the Great"
               :bio_blurb test-blurb
               :bio_photo "DSC_0306.jpg"
               :stry_photo_1 "DSC_0306.jpg"
               :stry_photo_2 "DSC_0306.jpg"
               :stry_photo_3 "DSC_0306.jpg"}]
   :users [{:id 1
            :username "admin"
            :pass "bcrypt+sha512$28be3213a164f464d1930554b438737a$12$fca83dc3b015704ffc15d943f91b976c96a522dd4121a896"
            :admin true}]})

(defn seed-db
  []
  (db/clear-students! *db*)
  (dorun (map (fn [s] (db/create-student! *db* s))
              (:students seed)))
  (db/clear-users! *db*)
  (dorun (map (fn [u] (db/create-user! *db* u))
              (:users seed))))
