# Logs
At startup program asks if we want to:
l - generate random logs,
r - convert logs,
s -show database,

When we want to generate logs, we need to enter how many do we want to generate, for example 100000 and enter.
Program informs that logs are saved in file log.txt.

Option convert logs, splits logs into two HashMaps with id as a key and JsonObject as a value.
After that program checks if log has start and finish, than checks if duration is longer than 4ms and add to final list transformed Json.
At the end program uses hibernate to map Json's to local storage HSQLDB database.

Option show database displays all records from database(events with duration time, alert and other informations).

