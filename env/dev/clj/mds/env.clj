(ns mds.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [mds.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[mds started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[mds has shut down successfully]=-"))
   :middleware wrap-dev})
