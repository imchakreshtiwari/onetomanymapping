# onetomanymapping
In this i am going to write the code for one to many mapping using Spring boot jpa.
the best way to model a one-to-many relationship is to use just @ManyToOne annotation on the child entity.

The second best way is to define a bidirectional association with a @OneToMany annotation on the parent side of the relationship and a @ManyToOne annotation on the child side of the relationship

This is known as Bidirectional Mapping It has some Pros and Cons.

In this section, we’ll define the domain models of our application - Post and Comment.
 
 created_at and updated_at comes automatically while using jpa.
 
 difference between one to one and onetomany is that in onetoone it is not possible to have a bidirectional relationship.
