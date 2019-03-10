(ns fib.core-test
  (:require [clojure.test :refer :all]
            [fib.core :refer :all]))

(deftest test-0
  (testing "n = 0"
    (is (= '(0) (fib 0)))))

(deftest test-1
  (testing "n = 1"
    (is (= '(0 1) (fib 1)))))

(deftest test-5
  (testing "n = 5"
    (is (= '(0 1 1 2 3 5) (fib 5)))))
