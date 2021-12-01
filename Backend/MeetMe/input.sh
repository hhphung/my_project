#!/bin/bash

read -r -p "Use localhost? [Y/n] " input
 
case $input in
    [yY][eE][sS]|[yY])
  echo "Yes"
  url='localhost:8080'
 ;;
    [nN][oO]|[nN])
  echo "No"
  url='coms-309-017.cs.iastate.edu:8080'
  ;;
    *)
 echo "Invalid input..."
 exit 1
 ;;
esac

curl -d '{"name": "Max", "password": "arst"}' -H 'Content-Type: application/json' "$url/user/"
curl -d '{"name": "Fred", "password": "arst"}' -H 'Content-Type: application/json' "$url/user/"
curl -d '{"name": "Alex", "password": "arst"}' -H 'Content-Type: application/json' "$url/user/"
curl -d '{"name": "Rick", "password": "arst"}' -H 'Content-Type: application/json' "$url/user/"

curl -d '{"name": "Admin1", "password": "arst", "role": "ADMIN"}' -H 'Content-Type: application/json' "$url/user/"
curl -d '{"name": "Admin2", "password": "arst", "role": "ADMIN"}' -H 'Content-Type: application/json' "$url/user/"
curl -d '{"name": "Admin3", "password": "arst", "role": "ADMIN"}' -H 'Content-Type: application/json' "$url/user/"

curl -d '{"name": "Meeting1", "adminName": "Admin1", "desc": "empty",
"dateTime": "", "street": "1234", "city": "Ames", "state": "IA",
"zipcode": 55555, "country": "USA"}' -H 'Content-Type: application/json' "$url/meeting/"

curl -d '{"name": "Meeting2", "adminName": "Admin2", "desc": "empty",
"dateTime": "", "street": "1234", "city": "Ames", "state": "IA",
"zipcode": 55555, "country": "USA"}' -H 'Content-Type: application/json' "$url/meeting/"

curl -d '{"name": "Meeting3", "adminName": "Admin3", "desc": "empty",
"dateTime": "", "street": "1234", "city": "Ames", "state": "IA",
"zipcode": 55555, "country": "USA"}' -H 'Content-Type: application/json' "$url/meeting/"

curl -d '{"userName": "Max", "meetingName": "Meeting1"}' -H 'Content-Type: application/json' "$url/meetingRequest/sendMeetingRequest"
curl -d '{"userName": "Max", "meetingName": "Meeting1"}' -H 'Content-Type: application/json' "$url/meetingRequest/acceptMeetingRequestNames"

curl -d '{"userName": "Rick", "meetingName": "Meeting2"}' -H 'Content-Type: application/json' "$url/meetingInvite/sendMeetingInvite"
curl -d '{"userName": "Rick", "meetingName": "Meeting2"}' -H 'Content-Type: application/json' "$url/meetingInvite/acceptMeetingInviteNames"

curl -d '{"userNameA": "Max", "userNameB": "Alex"}' -H 'Content-Type: application/json' "$url/user/sendFriendRequest"
curl -d '{"userName": "Max", "meetingName": "Alex"}' -H 'Content-Type: application/json' "$url/user/acceptFriendRequestNames"