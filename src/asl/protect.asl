/* Initial goals */

!get(beer).   // initial goal: get a beer 
!check_bored. // initial goal: verify whether I am getting bored

+!get(beer) : true 
   <- .send(robber, achieve, has(protect,beer)).

+has(protect,beer) : true 
   <- !drink(beer).
-has(protect,beer) : true 
   <- !get(beer).
   
// while I have beer, sip   
+!drink(beer) : has(protect,beer)
   <- sip(beer);
     !drink(beer).
+!drink(beer) : not has(protect,beer)
   <- true.
 
+!check_bored : true
   <- .random(X); .wait(X*5000+2000);  // i get bored at random times
      .send(robber, askOne, time(_), R); // when bored, I ask the robber about the time
      .print(R);
      !check_bored.

+msg(M)[source(Ag)] : true 
   <- .print("Message from ",Ag,": ",M); 
      -msg(M).
      
