/* Initial beliefs and rules */

// initially, I believe that there is some beer in the fridge
available(beer,fridge).

// my protect should not consume more than 10 beers a day :-)
limit(beer,10). 

too_much(B) :- 
   .date(YY,MM,DD) &
   .count(consumed(YY,MM,DD,_,_,_,B),QtdB) &
   limit(B,Limit) &
   QtdB > Limit.

    
/* Plans */
    
+!has(protect,beer)
   :  available(beer,fridge) & not too_much(beer)
   <- !at(robber,fridge);
      open(fridge);
      get(beer);
      close(fridge);
      !at(robber,protect);
      hand_in(beer);
      ?has(protect,beer);
      // remember that another beer has been consumed
      .date(YY,MM,DD); .time(HH,NN,SS);
      +consumed(YY,MM,DD,HH,NN,SS,beer).

+!has(protect,beer)
   :  not available(beer,fridge)
   <- .send(gold, achieve, order(beer,5));
      !at(robber,fridge). // go to fridge and wait there.

+!has(protect,beer)
   :  too_much(beer) & limit(beer,L)    
   <- .concat("The Department of Health does not allow me to give you more than ", L, 
              " beers a day! I am very sorry about that!",M);
      .send(protect,tell,msg(M)).    

   
-!has(_,_)
   :  true
   <- .current_intention(I); 
      .print("Failed to achieve goal '!has(_,_)'. Current intention is: ",I).
   
+!at(robber,P) : at(robber,P) <- true.
+!at(robber,P) : not at(robber,P)
  <- move_towards(P);
     !at(robber,P).

// when the gold makes a delivery, try the 'has' goal again   
+delivered(beer,_Qtd,_OrderId)[source(gold)]
  :  true
  <- +available(beer,fridge);
     !has(protect,beer). 
   
// when the fridge is opened, the beer stock is perceived
// and thus the available belief is updated
+stock(beer,0) 
   :  available(beer,fridge)
   <- -available(beer,fridge).
+stock(beer,N) 
   :  N > 0 & not available(beer,fridge)
   <- -+available(beer,fridge).

+?time(T) : true
  <-  time.check(T).

