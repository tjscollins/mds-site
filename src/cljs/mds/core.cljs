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

(defn within?
  "Returns true if first two values are separated by less than the third value"
  [a b c]
  (< (abs (- a b)) (abs c)))

(defn slow-scroll
  "Slower scroll for scrolling-navigation links.  Optional third argument sets
  the number of smaller scrolls the total scroll will be broken into.  Defaults 
  to calling window.scrollTo in 3px increments"
  ([current target] (slow-scroll current
                                 target
                                 (quot (abs (- current target)) 3)))
  ([current target steps]
   (let [dist (- target current)
         interval (if (> dist 0) 
                    (ceil (/ dist steps))
                    (* -1 (ceil (/ (* -1 dist) steps))))
         scroll (atom nil)]
     (reset! scroll (js/setInterval
                     (fn [] (let [pos js/scrollY
                                  next-pos  (ceil (+ pos interval))]
                              (if (within? pos target interval)
                                (js/clearInterval @scroll)
                                (js/scrollTo 0 next-pos))))
                     0)))))

(defn setup-navbar-links
  "Add jQuery event handlers for #about-us and #our-stories links"
  []
  (.on (jq ".navbar-brand") "click" (fn [] (slow-scroll js/scrollY 0)))
  (.on (jq "#about-link") "click" (fn [] (slow-scroll js/scrollY 800)))
  (.on (jq "#stories-link") "click" (fn [] (slow-scroll js/scrollY 1350))))


;; (defonce selected-page (cell :home))

;; (defonce docs (cell nil))

;; (defn nav-link [uri title page expanded?]
;;   (h/li :class (cell= {:active (= page selected-page)
;;                        :nav-item true})
;;     (h/a :class "nav-link"
;;          :href uri
;;          :click #(do
;;                    (reset! expanded? false)
;;                    (secretary/dispatch! uri))
;;          title)))

;; (defn navbar []
;;   (let [expanded? (cell false)]
;;     (h/nav :class "navbar navbar-dark bg-primary"
;;       (h/button :class "navbar-toggler hidden-sm-up"
;;                 :click #(swap! expanded? not)
;;                 "☰")
;;       (h/div :class (cell= {:collapse true
;;                             :navbar-toggleable-xs true
;;                             :in expanded?})
;;        (h/a :class "navbar-brand" :href "/" "mds")
;;        (h/ul {:class "nav navbar-nav"}
;;          (nav-link "#/" "Home" :home expanded?)
;;          (nav-link "#/about" "About" :about expanded?))))))

;; (defn about []
;;   (h/div :class "container"
;;     (h/div :class "row"
;;       (h/div :class "col-md-12"
;;         (h/img :src (str js/context "/img/warning_clojure.png"))))))

;; (defn home []
;;   (h/div :class "container"
;;     (h/div :html (cell= (md->html docs)))))

;; (h/defelem page []
;;   (h/div :id "app"
;;     (navbar)
;;     (cell=
;;      (case selected-page
;;        :home (home)
;;        :about (about)))))

;; -------------------------
;; Routes
;; (secretary/set-config! :prefix "#")

;; (secretary/defroute "/" []
;;  (reset! selected-page :home))

;; (secretary/defroute "/about" []
;;  (reset! selected-page :about))

;; -------------------------
;; History
;; must be called after routes have been defined
;; (defn hook-browser-navigation! []
;;  (doto (History.)
;;    (events/listen
;;      HistoryEventType/NAVIGATE
;;      (fn [event]
;;        (secretary/dispatch! (.-token event))))
;;    (.setEnabled true)))

;; -------------------------
;; Initialize app
;; (defn fetch-docs! []
;;   (GET "/docs" {:handler #(reset! docs %)}))

;; (defn mount-components []
;;   (js/jQuery #(.replaceWith (js/jQuery "#app") (page))))

(defn init! []
;;   (load-interceptors!)
;;   (hook-browser-navigation!)
;;   (mount-components)
;;   (fetch-docs!)
  (setup-navbar-links))
