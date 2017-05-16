(ns mds.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [mds.core-test]))

(doo-tests 'mds.core-test)

