# Tips

## Database migration

1. Prepare scripts to run sql
2. Dump real database dump
3. Migrate database into local database & run prepared scripts (use tools like liquibase)
4. (If 3 successed) Stop the running server
5. Apply scripts to real database (also migration)
