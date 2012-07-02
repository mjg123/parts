(ns mjg002.instrs
  (:use [overtone.live]))

(definst foo [note 60 vel 0.8 dur 3]
  (let [cps (midicps note)]
    (* vel
       (env-gen (envelope [1 0.0001] [dur] :exponential) :action FREE)
       (+ (sin-osc cps)
          (sin-osc (* 2 cps))))))
