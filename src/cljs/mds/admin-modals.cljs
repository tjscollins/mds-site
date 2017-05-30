(ns mds.admin-modals
  (:require [mds.ajax :refer [load-interceptors!]]
            [ajax.core :refer [GET]]
            [cljsjs.jquery]
            [cljsjs.bootstrap]
            [mds.homepage-init :refer [homepage]]
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

;; Render admin-modal forms via hoplon or om?
