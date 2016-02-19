(ns prob.dev
  (:require [prob.core :as core]))

(enable-console-print!)

(defn on-jsload []
  (core/main))
