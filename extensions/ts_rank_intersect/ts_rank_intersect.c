#include "postgres.h"
#include "fmgr.h"
#include "utils/builtins.h"
#include "executor/spi.h"
#include "catalog/pg_type.h"
#include <string.h>

#ifdef PG_MODULE_MAGIC
PG_MODULE_MAGIC;
#endif

PG_FUNCTION_INFO_V1(ts_rank_intersect);

int tokenize(char *, char ***);
float getTokenIDF(char *);
Datum ts_rank_intersect(PG_FUNCTION_ARGS);

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

float getTokenIDF(char *token)
{
    bool isNull;
    text *tokenTextP;
    Datum tokenDatum, idfDatum;
    Datum *args;
    Oid *argTypes;
    int spiResultCode;
    float idf;

    SPI_connect();
    tokenTextP = cstring_to_text(token);
    tokenDatum = PointerGetDatum(tokenTextP);
    argTypes = palloc(sizeof(Oid));
    argTypes[0] = TEXTOID;
    args = (Datum *) palloc(sizeof(Datum));
    args[0] = tokenDatum;

    spiResultCode = SPI_execute_with_args(
        "SELECT idf::real FROM IDF WHERE token = $1",
        1,
        argTypes,
        args,
        NULL,
        true,
        1
    );
    if(spiResultCode == SPI_OK_SELECT)
    {
        idfDatum = SPI_getbinval(SPI_tuptable->vals[0], SPI_tuptable->tupdesc, 1, &isNull);
        idf = DatumGetFloat4(idfDatum);
    } else {
        ereport(ERROR, (errmsg("Unable to query TokenDictionaryMaterialized table. Does it exist?")));
        idf = -1.0;
    }

    pfree(args);
    pfree(argTypes);
    pfree(tokenTextP);
    SPI_finish();

    return idf;
}

Datum ts_rank_intersect(PG_FUNCTION_ARGS)
{
	char **documentTokenList, **queryTokenList;
	char *document, *query;
	int documentWords, queryWords;
	int i, j;
    float score;

	document = text_to_cstring(PG_GETARG_TEXT_P(0));
	query = text_to_cstring(PG_GETARG_TEXT_P(1));

	documentWords = tokenize(document, &documentTokenList);
	queryWords = tokenize(query, &queryTokenList);

    score = 0.0;
	for(i = 0; i < queryWords; i++)
	{
		for(j = 0; j < documentWords; j++)
		{
			if(!strcmp(queryTokenList[i], documentTokenList[j]))
			    score += getTokenIDF(queryTokenList[i]);
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

	PG_RETURN_FLOAT4(score);
}
