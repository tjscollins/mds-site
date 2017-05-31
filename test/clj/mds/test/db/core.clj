(ns mds.test.db.core
  (:require [mds.db.core :refer [*db*] :as db]
            [mds.test.db.seed :refer [seed-db seed]]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [clojure.edn :as edn]
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
    (seed-db)
    (f)))

(def new-students [{:id 6
                    :first_name "Hamlet"
                    :last_name "the Dane"
                    :bio_blurb ""
                    :bio_photo ""
                    :stry_photo_1 ""
                    :stry_photo_2 ""
                    :stry_photo_3 ""}])

(deftest get-student
  (let [ids (range (count (:students seed)))]
    (dorun (map #(is (= (get-in seed [:students %1]) (db/get-student *db* {:id %1})))
                ids))))

(deftest create-student
  (db/create-student! *db* (nth new-students 0))
  (is (= (nth new-students 0) (db/get-student *db* {:id 6}))))

(deftest get-page
  )

(deftest create-page
  )

(deftest get-admin-params
  )

(deftest set-admin-params
  )
