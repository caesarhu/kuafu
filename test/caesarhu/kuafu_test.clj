(ns caesarhu.kuafu-test
  (:require [clojure.test :refer :all]
            [caesarhu.kuafu :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (let [model (sat/new-model)
          x (.newIntVar model 0 2 "x")
          y (.newIntVar model 0 2 "y")
          z (.newIntVar model 0 2 "z")
          _ (.addDifferent model x y)
          m (sat/solutions model [x y z])]))
    (is (= {:count 18, } 1)))
