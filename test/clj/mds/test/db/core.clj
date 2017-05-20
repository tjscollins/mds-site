(ns mds.test.db.core
  (:require [mds.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [mds.config :refer [env]]
            [mount.core :as mount]))

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
                              :bio_blurb "Lorem ipsum iacta est."})
    (db/create-student! *db* {:id 2
                              :first_name "Jane"
                              :last_name "Doe"
                              :bio_blurb "Lorem ipsum iacta test."})
    (f)))

(deftest test-students
  (is (= {:id 1
          :first_name "John"
          :last_name "Doe"
          :bio_blurb "Lorem ipsum iacta est."}
         (db/get-student *db* {:id 1}))))
