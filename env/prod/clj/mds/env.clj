(ns mds.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[mds started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[mds has shut down successfully]=-"))
   :middleware identity})
