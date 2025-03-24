output "eks_cluster_endpoint" {
  description = "Endpoint for EKS cluster"
  value       = aws_eks_cluster.main.endpoint
}

output "eks_cluster_name" {
  description = "Name of the EKS cluster"
  value       = aws_eks_cluster.main.name
}

output "eks_cluster_certificate_authority" {
  description = "Certificate authority data for EKS cluster"
  value       = aws_eks_cluster.main.certificate_authority[0].data
  sensitive   = true
}

output "rds_endpoint" {
  description = "Endpoint for RDS instance"
  value       = aws_db_instance.postgres.endpoint
}

output "redis_endpoint" {
  description = "Endpoint for Redis cluster"
  value       = aws_elasticache_cluster.redis.cache_nodes[0].address
}

output "vpc_id" {
  description = "ID of the VPC"
  value       = aws_vpc.main.id
}

output "public_subnet_ids" {
  description = "IDs of public subnets"
  value       = aws_subnet.public[*].id
}

output "route53_nameservers" {
  description = "Nameservers for Route53 zone"
  value       = aws_route53_zone.main.name_servers
}

output "acm_certificate_arn" {
  description = "ARN of ACM certificate"
  value       = aws_acm_certificate.main.arn
}

output "cloudwatch_log_group" {
  description = "Name of CloudWatch log group"
  value       = aws_cloudwatch_log_group.app_logs.name
}

output "backup_bucket_name" {
  description = "Name of S3 bucket for backups"
  value       = aws_s3_bucket.backups.id
}

output "security_group_ids" {
  description = "Map of security group IDs"
  value = {
    rds   = aws_security_group.rds.id
    redis = aws_security_group.redis.id
  }
}

output "kubeconfig" {
  description = "Kubeconfig for EKS cluster"
  value = templatefile("${path.module}/kubeconfig.tpl", {
    cluster_name    = aws_eks_cluster.main.name
    cluster_endpoint = aws_eks_cluster.main.endpoint
    cluster_ca      = aws_eks_cluster.main.certificate_authority[0].data
    region          = var.aws_region
  })
  sensitive = true
} 