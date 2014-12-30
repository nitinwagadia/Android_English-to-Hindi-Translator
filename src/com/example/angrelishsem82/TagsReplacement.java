package com.example.angrelishsem82;


public class TagsReplacement 
{

	public String replace(String x) 
	{
		x=x.replaceAll("[(][V][B][N] | [(][V][B][P] | [(][V][B][G] | [(][V][B][Z] | [(][V][B][D] | [(][V][B] | [(][M][D]"," (VPW ");
		   x=x.replaceAll("[(][W][H][N][P] | [(][W][H][A][D][V][P] | [(][W][H][A][D][J][P] | [(][W][H][P][P]"," (whP ");
		   x=x.replaceAll("[(][R][B][R] | [(][R][B][S] | [(][R][B]"," (adv ");
		   x=x.replaceAll("[(][J][J][R] | [(][J][J][S] | [(][J][J]"," (adj ");
		   x=x.replaceAll("[(][I][N] | [(][T][O] "," (prep ");
		return x;
	}


	
}
