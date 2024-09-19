package com.cmdpro.animancy.integration;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.registry.BlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.StairBlock;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

public class PatchouliMultiblocks {
    public static void register() {
        String[][] pattern = new String[][]
            {
                {
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     "
                },
                {
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     "
                },
                {
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     "
                },
                {
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     "
                },
                {
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     "
                },
                {
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     "
                },
                {
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "          e          ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     "
                },
                {
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "          b          ",
                        "        b   b        ",
                        "          E          ",
                        "       b S0N b       ",
                        "          W          ",
                        "        b   b        ",
                        "          b          ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     ",
                        "                     "
                },
                {
                        "__ddddddddddddddddd__",
                        "_ddddddddddddddddddd_",
                        "ddddddddddddddddddddd",
                        "ddddddddddddddddddddd",
                        "ddddddddddddddddddddd",
                        "ddddddddddddddddddddd",
                        "ddddddddddddddddddddd",
                        "ddddddddddddddddddddd",
                        "ddddddddddddddddddddd",
                        "ddddddddddddddddddddd",
                        "ddddddddddddddddddddd",
                        "ddddddddddddddddddddd",
                        "ddddddddddddddddddddd",
                        "ddddddddddddddddddddd",
                        "ddddddddddddddddddddd",
                        "ddddddddddddddddddddd",
                        "ddddddddddddddddddddd",
                        "ddddddddddddddddddddd",
                        "ddddddddddddddddddddd",
                        "_ddddddddddddddddddd_",
                        "__ddddddddddddddddd__"
                }
            };
        IMultiblock soulritual = PatchouliAPI.get().makeMultiblock(
                pattern,
                'd', PatchouliAPI.get().predicateMatcher(Blocks.AIR, (state) -> {
                    return state.isSolid();
                }),
                'N', PatchouliAPI.get().stateMatcher(Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH)),
                'S', PatchouliAPI.get().stateMatcher(Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH)),
                'E', PatchouliAPI.get().stateMatcher(Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST)),
                'W', PatchouliAPI.get().stateMatcher(Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST)),
                '0', PatchouliAPI.get().looseBlockMatcher(BlockRegistry.ECHOSOIL.get()),
                'e', PatchouliAPI.get().looseBlockMatcher(Blocks.SOUL_FIRE),
                'b', PatchouliAPI.get().stateMatcher(Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true)),
                ' ', PatchouliAPI.get().predicateMatcher(Blocks.AIR, (state) -> {
                    return !state.isSolid();
                })
        );
        IMultiblock soulritualnoflames = PatchouliAPI.get().makeMultiblock(
                pattern,
                'd', PatchouliAPI.get().predicateMatcher(Blocks.AIR, (state) -> {
                    return state.isSolid();
                }),
                'N', PatchouliAPI.get().stateMatcher(Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH)),
                'S', PatchouliAPI.get().stateMatcher(Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH)),
                'E', PatchouliAPI.get().stateMatcher(Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST)),
                'W', PatchouliAPI.get().stateMatcher(Blocks.DEEPSLATE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST)),
                '0', PatchouliAPI.get().looseBlockMatcher(BlockRegistry.ECHOSOIL.get()),
                'e', PatchouliAPI.get().predicateMatcher(Blocks.SOUL_FIRE, (state) -> {
                    return state.is(Blocks.SOUL_FIRE) || state.isAir();
                }),
                'b', PatchouliAPI.get().stateMatcher(Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true)),
                ' ', PatchouliAPI.get().predicateMatcher(Blocks.AIR, (state) -> {
                    return !state.isSolid();
                })
        );
        PatchouliAPI.get().registerMultiblock(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "soulritual"), soulritual);
        PatchouliAPI.get().registerMultiblock(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "soulritualnoflames"), soulritualnoflames);
    }
}
