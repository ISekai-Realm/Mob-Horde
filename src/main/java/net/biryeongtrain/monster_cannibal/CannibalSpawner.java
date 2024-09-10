package net.biryeongtrain.monster_cannibal;

import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.biryeongtrain.monster_cannibal.registry.MonsterCannibalBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.texture.GuiAtlasManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CannibalSpawner extends Block implements BlockEntityProvider, PolymerBlock {
    public CannibalSpawner(Settings settings) {
        super(settings);
    }
    
    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return null;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        var be = world.getBlockEntity(pos);
        
        if (!world.isClient && (be instanceof CannibalSpawnerEntity cannibalSpawnerEntity)) {
            if (cannibalSpawnerEntity.isEnabled()) {
                new Gui((ServerPlayerEntity) player);
                return ActionResult.SUCCESS;
            }
        }
        
        return ActionResult.FAIL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CannibalSpawnerEntity(pos, state);
    }
 
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!world.isClient) {
            if (type == MonsterCannibalBlockRegistry.CANNIBAL_SPAWNER_ENTITY) {
                return (world1, pos, state1, blockEntity) -> CannibalSpawnerEntity.serverTick(world1, state1, pos, (CannibalSpawnerEntity) blockEntity);
            }
        }
        
        return null;
    }
    
    private class Gui extends SimpleGui {
        public Gui(ServerPlayerEntity player) {
            super(ScreenHandlerType.GENERIC_9X6, player, false);
            this.setTitle(Text.literal("test"));
        }
    }
}
