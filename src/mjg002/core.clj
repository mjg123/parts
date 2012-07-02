(ns mjg002.core
  (:use [overtone.live])
  (:use [mjg002.instrs]))

(defn part
  "Returns a function which takes a metronome and a starting beat.
   When called, that function enqueues the notes evenly, spacing beats apart."
  [name inst spacing & notes]
  (println notes)
  (fn [m t]
    (dorun
     (map-indexed
      (fn [i note]
        (when (not (nil? note))
          (at (m (+ (* spacing i) t)) (apply inst note))))
      notes))))

(defn gart
  "Groups of parts.  Given a bunch of parts, returns a fn which
   plays them spacing apart."
  [name spacing & parts]
  (fn [m t]
    (dorun
     (map-indexed
      (fn [i prt]
        (if (sequential? prt)
          nil
          (prt m (+ (* spacing i) t))))
      parts))))

(let [m (metronome 160)

      p1 (part :arp1 foo 0.5 [60] [67] [72] [67] [60] [67])
      p2 (part :arp2 foo 0.5 [60] nil  [72] nil  [60] [67])

      b1 (part :bas1 foo 3 [48] [43])

      tarps (gart :thre 3 [p1 b1] [p2] [p1 b1] [p2])]
  (tarps m 0)
  (barps m 0))
