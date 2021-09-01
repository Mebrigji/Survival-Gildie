package qu.moon.learn;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import qu.moon.learn.commands.admin.ConfigCommand;
import qu.moon.learn.commands.premium.FlyCommand;
import qu.moon.learn.commands.admin.GameModeCommand;
import qu.moon.learn.commands.MoneyCommand;
import qu.moon.learn.commands.TestCommand;
import qu.moon.learn.data.actionbar.BetterActionBar;
import qu.moon.learn.data.config.Config;
import qu.moon.learn.data.drop.Drop;
import qu.moon.learn.data.user.User;
import qu.moon.learn.db.DataService;
import qu.moon.learn.db.system.ElementType;
import qu.moon.learn.db.system.TableConstructor;
import qu.moon.learn.listeners.PlayerListener;
import qu.moon.learn.service.ServiceManager;

import java.util.Arrays;

//class Main : JavaPlugin() {
//  companion object {
//    lateinit var instance: Main
//  }
//
//  override fun onEnable() {
//    instance = this
//  }
//}
//    val serviceManager: ServiceManager = ServiceManager()
//    private var dataBase: DataBase = DataBase()
//
//    val kotlinConfig: Config = Config(this)
//
//    fun getDataBase(): DataBase{
//        return dataBase
//    }
//
//    override fun onEnable() {
//
//        kotlinConfig.load(true)
//        kotlinConfig.getOrCreate("test", mutableListOf("abc"))
//
//        dataBase.connect("localhost", "root", "", "test", 3306, true)
//
//        arrayOf(GameModeCommand(), FlyCommand(), MoneyCommand(), TestCommand()).forEach { command ->
//            serviceManager.commandService.register(command)
//        }
//
//        dataBase.createTable("users")
//            .create(arrayOf(TableConstructor("name", ElementType.VARCHAR, 32, true)))
//
//        dataBase.createTable("drops")
//            .create(arrayOf(
//                TableConstructor("id", ElementType.INT, 5, true),
//                TableConstructor("serializeCode", ElementType.VARCHAR, 1000),
//                TableConstructor("item", ElementType.VARCHAR, 1000)
//        ))
//
//        serviceManager.userService
//            .getUserList()
//            .addAll(dataBase.download("users", User::class.java))
//
//        serviceManager.dropService
//            .getDropList().addAll(dataBase.download("drops", Drop::class.java))
//
//    }
//
//}

@Getter
@Setter
public class Main extends JavaPlugin {

    protected static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public Main() {
        instance = this;
    }

    private final ServiceManager serviceManager = new ServiceManager();

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    private final DataService dataService = new DataService();

    public DataService getDataService() {
        return dataService;
    }

    private Config kotlinConfig;

    public Config getKotlinConfig() {
        return kotlinConfig;
    }

    public void onEnable() {

        kotlinConfig = new Config(this);
        kotlinConfig.load(true);


        dataService.connect("localhost", "root", "", "test", 3306, true);

        Arrays.asList(
                new GameModeCommand(), new FlyCommand(),
                new MoneyCommand(), new ConfigCommand(),
                new TestCommand()).forEach(getServiceManager().getCommandService()::register);

        dataService.createTable("users")
                .create(new TableConstructor[]{new TableConstructor("name", ElementType.VARCHAR, 32, true)});

        dataService.createTable("drops")
                .create(new TableConstructor[]{new TableConstructor("id", ElementType.INT, 5, true),
                        new TableConstructor("serializeCode", ElementType.VARCHAR, 1000),
                        new TableConstructor("item", ElementType.VARCHAR, 1000)});

        serviceManager.getUserService()
                .getUserList()
                .addAll(dataService.download("users", User.class));

        serviceManager.getDropService()
                .getDropList()
                .addAll(dataService.download("drops", Drop.class));

        Arrays.asList(new PlayerListener()).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));

        //Runnables

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            getServiceManager().getBetterActionBarService().getActionBarList().stream().filter(BetterActionBar::isOnline).forEach(BetterActionBar::sendActionBar);
        }, 0, 20);

    }
}
