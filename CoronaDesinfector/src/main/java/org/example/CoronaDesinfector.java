package org.example;

// 7 Responsabilities line 6,7 choose implementation, method desinfect
// configure AnnouncerImpl, PolicemanImpl
// create instance AnnouncerImpl, PolicemanImpl
public class CoronaDesinfector {

;    private Announcer announcer = ObjectFactory.getInstance().createObject(Announcer.class);
    private Policeman policeman = ObjectFactory.getInstance().createObject(Policeman.class);

    public void start (Room room){
        announcer.announce("Starting desifenction, all out!");
        policeman.makePeopleLeaveRoom();
        desinfect(room);
        announcer.announce("you can enter again");
    }

    private void desinfect(Room room){
        System.out.println("Molitva korona izidi from room " + room);
    }
}
