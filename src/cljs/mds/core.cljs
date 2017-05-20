(ns mds.core
  (:require [mds.ajax :refer [load-interceptors!]]
            [ajax.core :refer [GET]]
            [cljsjs.jquery]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [hoplon.core
             :as h
             :include-macros true]
            [hoplon.jquery]
            [javelin.core
             :refer [cell]
             :refer-macros [cell= dosync]]
            [markdown.core :refer [md->html]]
            [secretary.core :as secretary])
  (:import goog.History))

(def jq js/jQuery)
(defn ceil [n] (.ceil js/Math n))
(defn abs [n]  (.abs js/Math n))

(defn slow-scroll
  "Slower scroll for scrolling-navigation links.  Optional third argument sets the 
  duration of the scroll.  Defaults to 2ms per pixel distance, resulting in a nice, 
  slow scroll."
  ([current target] (slow-scroll current
                                 target
                                 (* 2 (abs (- target current)))))
  ([current target duration] (slow-scroll current target duration #()))
  ([current target duration callback]
   (let [aniProps (clj->js {:scrollTop target})
         aniOpts (clj->js {:duration duration
                           :complete callback})]
     (.animate (jq "body") aniProps aniOpts))))

(defn setup-navbar-links
  "Add jQuery event handlers for #about-us and #our-stories links"
  []
  (.on (jq ".navbar-brand") "click"
       (fn [] (slow-scroll js/scrollY 0)))
  (.on (jq "#about-link") "click"
       (fn []
         (let [current js/scrollY]
           (if (> current 375)
             (slow-scroll current 800)
             (slow-scroll current 375 750 (fn [] (js/setTimeout
                                                     #(slow-scroll 375 800)
                                                     1500)))))))  
  (.on (jq "#stories-link") "click"
       (fn [] (slow-scroll js/scrollY 1350))))

(defn setup-homepage-header-color-changes
  "Add skrollr data attributes for text color changes in header"
  []
  (if (= "/" (aget js/location "pathname"))
    (let [selector (jq ".nav a, a.navbar-brand")]
      (.attr selector "data-1000" "color: rgb(0,0,0);")
      (.attr selector "data-1200" "color: rgb(255, 255, 255);"))))

(defn init! []
  (setup-navbar-links)
  (setup-homepage-header-color-changes))
