(ns caesarhu.kuafu.util)

(defn rand-letter-str
  [^long len]
  (transduce
   (map (fn [_]
          (let [rnd (rand-int 52)]
            (char (+ (mod rnd 26)
                     (int (if (< rnd 26) \a \A)))))))
   str
   ""
   (range len)))

(defn rand-name
  []
  (rand-letter-str 10))