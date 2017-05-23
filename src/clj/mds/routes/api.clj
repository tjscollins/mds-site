(ns mds.routes.api
  (:require [mds.db.core :refer [*db*] :as db]
            [compojure.core :refer [defroutes POST]]
            [ring.util.http-response :as response]
            [clojure.walk :as walk]))

(defroutes api-routes
  (POST "/student" request
       (let [{body :body} request]
         (let [student (walk/keywordize-keys body)]
           (try
             (db/create-student! student)
             (response/ok {:saved true
                           :error nil
                           :student student})
             (catch Exception e
               (response/bad-request {:saved false
                                      :error (str e)
                                      :student nil})))))))
