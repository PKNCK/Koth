package io.github.aquerr.koth.storage;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.aquerr.koth.Koth;
import io.github.aquerr.koth.entity.Arena;
import io.github.aquerr.koth.entity.ArenaClass;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class StorageManagerImpl implements StorageManager
{
    private final Koth plugin;

    private final ArenaStorage arenaStorage;
    private final ArenaClassStorage arenaClassStorage;

    @Inject
    public StorageManagerImpl(final Koth plugin, final ArenaStorage arenaStorage, final ArenaClassStorage arenaClassStorage)
    {
        this.plugin = plugin;
        this.arenaStorage = arenaStorage;
        this.arenaClassStorage = arenaClassStorage;

        //Prepare storage directory
        if(Files.notExists(this.plugin.getConfigDir()))
        {
            try
            {
                Files.createDirectory(this.plugin.getConfigDir());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        Path storageDirPath = plugin.getConfigDir().resolve("storage");
        if(Files.notExists(storageDirPath))
        {
            try
            {
                Files.createDirectory(storageDirPath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Arena> getArenas()
    {
        try
        {
            return this.arenaStorage.getArenas();
        }
        catch (ObjectMappingException e)
        {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Arena getArena(final String arenaName)
    {
        return null;
    }

    @Override
    public boolean addArena(final Arena arena) throws ObjectMappingException
    {
        return this.arenaStorage.addArena(arena);
    }

    @Override
    public boolean updateArena(final Arena arena)
    {
        try
        {
            return this.arenaStorage.updateArena(arena);
        }
        catch (final ObjectMappingException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteArena(final String name)
    {
        return this.arenaStorage.deleteArena(name);
    }

    @Override
    public List<ArenaClass> getArenaClasses()
    {
        try
        {
            return this.arenaClassStorage.getArenaClasses();
        }
        catch(ObjectMappingException e)
        {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean addArenaClass(final ArenaClass arenaClass) throws ObjectMappingException
    {
        return this.arenaClassStorage.addArenaClass(arenaClass);
    }

    @Override
    public boolean updateArenaClass(final ArenaClass arenaClass)
    {
        try
        {
            return this.arenaClassStorage.updateArenaClass(arenaClass);
        }
        catch(ObjectMappingException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteArenaClass(final String name)
    {
        return this.arenaClassStorage.deleteArenaClass(name);
    }
}
