(ns caesarhu.kuafu.sat-test
  (:require [clojure.test :refer :all]
            [caesarhu.kuafu.sat :as sat]))

(deftest solutions-test
  (testing "cp-sat solutions test."
    (let [model (sat/new-model)
          x (.newIntVar model 0 2 "x")
          y (.newIntVar model 0 2 "y")
          z (.newIntVar model 0 2 "z")
          _ (.addDifferent model x y)
          result (sat/solutions model [x y z])]
      (is (= {:count 18, :status "OPTIMAL"} (dissoc result :values))))))