(ns mds.core
  (:require [mds.ajax :refer [load-interceptors!]]
            [ajax.core :refer [GET]]
            [cljsjs.jquery]
            [cljsjs.bootstrap]
            [mds.homepage-init :refer [homepage]]
            ;; [goog.events :as events]
            ;; [goog.history.EventType :as HistoryEventType]
            ;; [hoplon.core
            ;;  :as h
            ;;  :include-macros true]
            ;; [hoplon.jquery]
            ;; [javelin.core
            ;;  :refer [cell]
            ;;  :refer-macros [cell= dosync]]
            ;; [markdown.core :refer [md->html]]
            ;;[secretary.core :as secretary]
            )
  (:import goog.History))

(def jq js/jQuery)
(defn setup-story-switching
  []
  (let [story-thumbnails (jq ".story-thumbnail-box")
        stories (jq ".student-story")]
    (.on story-thumbnails "click"
         (fn []
           (this-as this
             (let [id (.attr (jq this) "student-id")]
               (.css stories "display" "none")
               (.css (jq (str "#student-story-" id)) "display" "block")))))
    (.css (jq (aget stories 2)) "display" "block")))

(defn jqmodal
  [s]
  (jq (str s "-modal")))

(defn jqlink
  [s]
  (jq (str s "-link")))

(defn setup-admin-modals
  "Add event handlers for modal links via jQuery"
  []
  (let [elems ["#edit-pages" "#add-pages"
               "#edit-students" "#add-students"
               "#add-photos" "#del-photos"
               "#create-backup" "#restore-backup"]]
    (dorun
     (map (fn [elem] (.on (jqlink elem)
                          "click"
                          #(.modal (jqmodal elem))))
          elems))))

(defn init! []
  (load-interceptors!)
  (let [path (aget js/location "pathname")]
    (if (= "/" path)
      (homepage))
    (if (= "/stories" path)
      (setup-story-switching))
    (if (= "/admin" path)
      (setup-admin-modals))))
