package qu.moon.learn.data.user

class UserService {

    private val list:MutableList<User> = mutableListOf()

    fun getUserList(): MutableList<User>{
        return list
    }

    fun findUser(name:String): User {
        return list.toMutableList()
            .stream()
            .filter { user -> user.name == name }
            .findFirst()
            .orElse(create(User(name)))
    }

    fun create(user: User): User {
        if(list.contains(user)) return user
        list.add(user)
        return user
    }

}