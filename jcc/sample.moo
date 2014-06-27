"Sample program to test Bob's MOO parser";
"The code does not do anything useful!!!";

"Simple IF";
if (42)
  x = 1;
endif

"...with 'elseif'";
if (42)
  ok = 1;
elseif (21)
  ok = 0;
endif

"...with multiple 'elseif'";
if (42)
  ok = 1;
elseif (21)
  ok = 2;
elseif (7)
  ok = 0;
endif

"...with multiple 'elseif' and else";
if (42)
  ok = 1;
elseif (21)
  ok = 2;
elseif (7)
  ok = 0;
else 
  hmm = 1;
endif

"While loop";
while (42)
  x = 3;
  y = y * 2;
endwhile

"Empty while";
while (999)
endwhile

"Fork statement";
fork (5)
  x = 99;
  fred = 27;
endfork

"Try ...finally";
try
  x = 2;
finally
  end = 1;
endtry

"Try..except";
try
  uid = 21;
except (ALL)
  fred = 99;
endtry

"Try..multiple except";
try
  uid = 21;
except (E_VERBNF)
  fred = 99;
except (E_PROPNF, E_PERM)
  tom = 42;
endtry

"Simple break";
break;

"Break with label";
break fred;

"Continue";
continue;

"Continue with label";
continue do_more;

"Calling a builtin with args";
p = connected_players(me);

"Calling a builtin with no args";
p = connected_players();

"calling a verb on an object";
#42:say("Hello");

"Object assignment";
fyrem = #8833;

"Setting a property";
#42.idle = 1;

"Setting property via a variable";
fyren.ideas = 27;

