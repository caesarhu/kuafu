(ns caesarhu.kuafu.sat.solver-test
  (:require [clojure.test :refer :all]
            [caesarhu.kuafu.sat.model :as m]
            [caesarhu.kuafu.sat.solver :as s])
  (:import [com.google.ortools.sat SatParameters CpSolverResponse]))

(deftest solver-test 
  (testing "callback test."
    (let [model (m/sat-model)
          x (m/int-var model 1 10)
          y (m/bool-var model "y")
          solver (s/sat-solver)
          _ (reset! s/*solutions* (list))
          _ (m/add-greater-than model x 5)
          _ (s/set-all-solutions solver true)
          status (s/solve solver model (s/callback x))]
      (is (= "OPTIMAL" (str status)))
      (is (= 10 (count @s/*solutions*)))
      (is (every? #(< 5 % 11) @s/*solutions*))
      (is (double? (s/objective-value solver)))
      (is (double? (s/best-objective-bound solver)))
      (is (double? (s/wall-time solver)))
      (is (double? (s/user-time solver)))
      (is (s/value solver x) (first @s/*solutions*))
      (is (= [] (s/sufficient-assumptions-for-infeasibility solver)))
      (is (instance? com.google.ortools.sat.SatParameters$Builder (s/get-parameters solver)))
      (is (instance? com.google.ortools.sat.CpSolverResponse (s/response solver)))
      (is (string? (s/response-stats solver)))
      (is (string? (s/get-solution-info solver)))
      (is (int? (s/num-branches solver)))
      (is (int? (s/num-conflicts solver)))
      (is (boolean? (s/boolean-value solver y))))))
