#include "postgres.h"
#include "fmgr.h"
#include "utils/builtins.h"
#include <string.h>

#ifdef PG_MODULE_MAGIC
PG_MODULE_MAGIC;
#endif

PG_FUNCTION_INFO_V1(ts_rank_intersect);

int tokenize(char *, char ***);
Datum intersect_rank_func(PG_FUNCTION_ARGS);

int tokenize(char *string, char ***outArray)
{
        int tokens;
        char *token;

        tokens = 0;
        token = strtok(string, " '");
        while(token != NULL)
        {
                if(tokens == 0)
                        *outArray = palloc(sizeof(char *));
                else
                        *outArray = repalloc(*outArray, sizeof(char *) * (tokens + 1));
                (*outArray)[tokens] = palloc(strlen(token) + 1);
		memset((*outArray)[tokens], 0, strlen(token) + 1);
                strncpy((*outArray)[tokens], token, strlen(token));
                tokens++;
                token = strtok(NULL, " '");
        }
        return tokens;
}

Datum ts_rank_intersect(PG_FUNCTION_ARGS)
{
	char **documentTokenList, **queryTokenList;
	char *document, *query;
	int documentWords, queryWords;
	int *intersections;
	int intersectionIdx, i, j;

	document = text_to_cstring(PG_GETARG_TEXT_P(0));
	query = text_to_cstring(PG_GETARG_TEXT_P(1));

	documentWords = tokenize(document, &documentTokenList);
	queryWords = tokenize(query, &queryTokenList);

	intersections = malloc(sizeof(int) * queryWords);
	intersectionIdx = 0;
	for(i = 0; i < queryWords; i++)
	{
		for(j = 0; j < documentWords; j++)
		{
			if(!strcmp(queryTokenList[i], documentTokenList[j]))
			{
				intersections[intersectionIdx] = i;
				intersectionIdx++;
			}
		}
	}

	pfree(document);
	pfree(query);
	for(i = 0; i < queryWords; i++)
		pfree(queryTokenList[i]);
	for(i = 0; i < documentWords; i++)
		pfree(documentTokenList[i]);
	pfree(queryTokenList);
	pfree(documentTokenList);

	PG_RETURN_INT32(intersectionIdx);
}
