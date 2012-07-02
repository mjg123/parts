(ns parts.instrs
  (:use [overtone.live]))

(definst foo [note 60 vel 0.6 dur 3]
  (let [cps (midicps note)
        declick (env-gen (envelope [0 1 1 0] [0.01 (- dur 0.02) 0.01]))]

    (* vel
       (env-gen (envelope [1 0.0001] [dur] :exponential) :action FREE)
       (rlpf (saw cps) (* 1.1 cps) 0.4))))
