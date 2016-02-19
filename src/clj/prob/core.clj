(ns prob.core
  (:require [prob.server :as server])
  (:gen-class))


(defn start! []
  (server/start!))

(defn -main []
  (start!))
