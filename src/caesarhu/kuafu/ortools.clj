(ns caesarhu.kuafu.ortools
  (:require [redelay.core :refer [state status stop]])
  (:import [com.google.ortools Loader]))

(def ortools-loader
  "Load native libraries needed for using ortools-java."
  (state (Loader/loadNativeLibraries)))
