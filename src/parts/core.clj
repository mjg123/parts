(ns parts.core
  (:use [overtone.live])
  (:use [parts.instrs]))

(defn part
  "Returns a function which takes a metronome and a starting beat.
   When called, that function enqueues the notes evenly, spacing beats apart."
  [name inst spacing & notes]

  (fn [m t]
    (dorun
     (map-indexed
      (fn play-at [i note]
        (cond
          (nil? note) nil
          (sequential? (first note)) (dorun (map (fn [n] (play-at i n)) note))
          :elsewise (at (m (+ (* spacing i) t)) (apply inst note))))
      notes))))

(defn grpart
  "Groups of parts.  Given a list of lists of parts, returns a fn which
   plays them spacing apart."
  [name spacing & partlists]
  (fn [m t]
    (dorun
     (map-indexed
      (fn [i prts]
        (dorun (map (fn [p] (p m (+ (* spacing i) t))) prts)))
      partlists))))

(let [m (metronome 160)
      p1 (part :arp1 foo 0.5 [60] [67] [72] [67] [60] [67])
      c1 (part :cntr foo 0.5 nil  [59] [58] [57] [56] [55])

      g1 (grpart :grp1 3 [p1] [p1] [p1] [p1 c1])
      g2 (grpart :grp1 12 [g1] [g1] [g1] [g1])]

  (g2 m 0))

(stop)
