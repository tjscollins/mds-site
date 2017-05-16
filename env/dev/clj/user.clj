(ns user
  (:require [mount.core :as mount]
            [mds.figwheel :refer [start-fw stop-fw cljs]]
            mds.core))

(defn start []
  (mount/start-without #'mds.core/repl-server))

(defn stop []
  (mount/stop-except #'mds.core/repl-server))

(defn restart []
  (stop)
  (start))


