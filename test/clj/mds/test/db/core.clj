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

(deftest test-students
  (is (= (get-in db-seed [:students 0])
         (db/get-student *db* {:id 1}))))
