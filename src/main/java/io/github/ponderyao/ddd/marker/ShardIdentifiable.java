package io.github.ponderyao.ddd.marker;

/**
 * ShardIdentifiable：可分片标识状态
 *
 * @author PonderYao
 * @since 1.1.0
 */
public interface ShardIdentifiable<SID extends Identifier> {
    
    SID getShardId();
    
}
