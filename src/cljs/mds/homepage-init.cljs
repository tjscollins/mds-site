(ns mds.homepage-init
  (:require [cljsjs.jquery]))

(def jq js/jQuery)
(defn ceil [n] (.ceil js/Math n))
(defn abs [n]  (.abs js/Math n))

(def breakpoint [768 900 1200])
(def aws-url "https://s3-us-west-1.amazonaws.com/mdscontentfiles/MDS_2017/")
(def bg-dirs ["Preview" "736x460/" "1068x648/" "1440x900/"])


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
       (fn [e] (do (.preventDefault e)
                   (.css (jq "#mds-grp-photo-overlay, .backdrop-text") "opacity" 1)
                   (slow-scroll js/scrollY 0 1000
                                (fn []
                                  (.fadeTo (jq "#mds-grp-photo-overlay, .backdrop-text") 1000 0))))))
  (.on (jq "#about-link") "click"
       (fn []
         (slow-scroll js/scrollY 600)))
  (.on (jq "#stories-link") "click"
       (fn [] (slow-scroll js/scrollY 1350))))

(defn setup-homepage-header-color-changes
  "Add skrollr data attributes for text color changes in header"
  []
  
  (let [selector (jq ".nav a, a.navbar-brand")]
    (.attr selector "data-600" "color: rgb(0,0,0);")
    (.attr selector "data-900" "color: rgb(255, 255, 255);")))


(defn mobile?
  []
  (let [width (.-innerWidth js/window)]
    (< width (first breakpoint))))

(defn tablet?
  []
  (let [width (.-innerWidth js/window)]
    (and  (not (mobile?))
          (< width (last breakpoint)))))

(defn desktop?
  []
  (let [width (.-innerWidth js/window)]
    (and (not (tablet?))
         (not (mobile?))
         (> width (last breakpoint)))))

(defn image-url
  "Create image-url based on resolution"
  [res image]
  (str aws-url
       (get bg-dirs res)
       image))

(defn load-images
  "Load higher-res images based on screen size to replace low-res preview backgrounds"
  []
  (let [first-image (jq "#mds-grp-photo")
        second-image (jq "#mds-grp-photo-2")
        grp-photo-1 (.attr first-image "data-photo")
        grp-photo-2 (.attr second-image "data-photo")]
    (if (mobile?)
      (do
        (.attr first-image "src" (image-url 1 grp-photo-1))
        (.attr second-image "src" (image-url 1 grp-photo-2))))
    (if (tablet?)
      (do (.attr first-image "src" (image-url 2 grp-photo-1))
          (.attr second-image "src" (image-url 2 grp-photo-2))))
    (if (desktop?)
      (do (.attr first-image "src" (image-url 3 grp-photo-1))
          (.attr second-image "src" (image-url 3 grp-photo-2))))
    (.on first-image "load" (fn [] (do (.fadeTo (jq "#mds-grp-photo-overlay, .backdrop-text") 1000 0)
                                       (.fadeTo (jq "#mds-grp-photo") 1000 1)
                                       )))
    ))

(defn homepage
  "Code to run to setup home page"
  []
  (setup-navbar-links)
  ;; (setup-homepage-header-color-changes)
  (load-images))
