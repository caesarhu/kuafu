(ns caesarhu.kuafu.ortools
  (:require [redelay.core :refer [state status stop]])
  (:import [com.google.ortools Loader]))

(def ortools-loader
  (state (Loader/loadNativeLibraries)))
