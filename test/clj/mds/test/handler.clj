(ns mds.test.handler
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json]
            [clojure.walk :as walk]
            [ring.mock.request :as mock]
            [mds.handler :refer [app]]))

(deftest test-web-routes
  (testing "GET /"
    (let [response ((app) (mock/request :get "/"))]
      (is (= 200 (:status response)))))

  (testing "GET /stories"
    (let [response ((app) (mock/request :get "/stories"))]
      (is (= 200 (:status response)))))

  (testing "not-found route"
    (let [response ((app) (mock/request :get "/invalid"))]
      (is (= 404 (:status response))))))

(deftest test-api-routes
  (testing "POST /student"
    (let [student {:id 1234
                   :first_name "Johnny"
                   :last_name "Apple Seed"
                   :bio_blurb "Lorem ipsum iacta test."
                   :bio_photo "bio_photo.jpg"
                   :stry_photo_1 "stry_photo_1.jpg"
                   :stry_photo_2 "stry_photo_2.jpg"
                   :stry_photo_3 "stry_photo_3.jpg"}
          response ((app) (-> (mock/request :post "/student")
                              (mock/body (json/write-str student))
                              (mock/content-type "application/json")))
          {status :status body :body} response
          {saved :saved error :error} (walk/keywordize-keys (json/read-str body))]
      (is (= status 200))
      (is (= saved true))
      (is (= error nil)))
    (let [student {:first_name "Johnny"
                   :last_name "Apple Seed"
                   :bio_blurb "Lorem ipsum iacta test."
                   :bio_photo "bio_photo.jpg"
                   :stry_photo_1 "stry_photo_1.jpg"
                   :stry_photo_2 "stry_photo_2.jpg"
                   :stry_photo_3 "stry_photo_3.jpg"}
          response ((app) (-> (mock/request :post "/student")
                              (mock/body (json/write-str student))
                              (mock/content-type "application/json")))
          {status :status body :body} response
          {saved :saved error :error} (walk/keywordize-keys (json/read-str body))]
      (is (= status 400))
      (is (= saved false))
      (is (not= error nil)))))
