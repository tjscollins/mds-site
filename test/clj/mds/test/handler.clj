(ns mds.test.handler
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [mds.handler :refer :all]))

(deftest test-web-routes
  (testing "GET /"
    (let [response ((app) (request :get "/"))]
      (is (= 200 (:status response)))))

  (testing "GET /stories"
    (let [response ((app) (request :get "/stories"))]
      (is (= 200 (:status response)))))

  (testing "not-found route"
    (let [response ((app) (request :get "/invalid"))]
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
          response ((app) (request :post "/student" student))]
      (is (= 200 (:status response))))))
